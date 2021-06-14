/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.seb.currencyconverter.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.seb.currencyconverter.model.CurrencyRate;
import se.seb.currencyconverter.service.CurrencyConverterService;
import se.seb.currencyconverter.utils.HttpClient;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UnitTest {

	@Autowired
	private CurrencyConverterService currencyConverterService;

	@MockBean
	private HttpClient httpClient;

	private final String VALID_XML = "<gesmes:Envelope xmlns:gesmes=\"http://www.gesmes.org/xml/2002-08-01\" xmlns=\"http://www.ecb.int/vocabulary/2002-08-01/eurofxref\">\n" +
			"<gesmes:subject>Reference rates</gesmes:subject>\n" +
			"<gesmes:Sender>\n" +
			"<gesmes:name>European Central Bank</gesmes:name>\n" +
			"</gesmes:Sender>\n" +
			"<Cube>\n" +
			"<Cube time=\"2021-06-09\">\n" +
			"<Cube currency=\"USD\" rate=\"1.2195\"/>" +
			"</Cube>\n" +
			"</Cube>\n" +
			"</gesmes:Envelope>";

	@Test
	void testOkXml() {
		var expectedRates = new ArrayList<CurrencyRate>();
		CurrencyRate mockedCurrency = new CurrencyRate("USD", "1.2195");
		expectedRates.add(mockedCurrency);

		Mockito.when(httpClient.requestData(any(), any(), any())).thenReturn(VALID_XML);

		assertThat(expectedRates).containsExactlyInAnyOrderElementsOf(currencyConverterService.getTodaysCurrencies());
	}

	@Test
	void testCache() {
		//Loads the cache
		testOkXml();
		Mockito.when(httpClient.requestData(any(), any(), any())).thenReturn("NOT_VALID");

		var expectedRates = new ArrayList<CurrencyRate>();
		CurrencyRate mockedCurrency = new CurrencyRate("USD", "1.2195");
		expectedRates.add(mockedCurrency);
		//The NOT_VALID should not be fetched since data has been cached
		assertThat(expectedRates).containsExactlyInAnyOrderElementsOf(currencyConverterService.getTodaysCurrencies());
	}

}
