package se.seb.currencyconverter.service;

import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import se.seb.currencyconverter.Properties;
import se.seb.currencyconverter.ecb.EcbIntegration;
import se.seb.currencyconverter.model.CurrencyRate;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class CurrencyConverterService {
    private final EcbIntegration ecbIntegration;
    private final Properties properties;
    @Inject
    public CurrencyConverterService(EcbIntegration ecbIntegration, Properties properties) {
        this.ecbIntegration = ecbIntegration;
        this.properties = properties;
    }

    public List<CurrencyRate> getTodaysCurrencies() {
        var response = ecbIntegration.requestDataFromECB();

        return extractCurrencyData(response);
    }

    private List<CurrencyRate> extractCurrencyData(String xmlData) {
        List<CurrencyRate> currRateList = new ArrayList<>();
        try {
            var db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final var is = new InputSource();
            is.setCharacterStream(new StringReader(xmlData));
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile("//Cube/Cube/Cube");
            NodeList nl = (NodeList) expr.evaluate(db.parse(is), XPathConstants.NODESET);
            IntStream.range(0, nl.getLength()).mapToObj(nl::item).filter(node->node.getAttributes().getLength() > 0)
                .forEach(node -> {
                    NamedNodeMap attribs = node.getAttributes();
                    currRateList.add(new CurrencyRate(attribs.getNamedItem("currency").getNodeValue(), attribs.getNamedItem("rate").getNodeValue()));
            });

        } catch (SAXException | IOException | XPathExpressionException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return currRateList;
    }
}
