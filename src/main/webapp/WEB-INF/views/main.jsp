<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <title>SEB currency converter</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style type="text/css">
        .center {
            vertical-align: baseline;
        }
    </style>
    <script type="text/javascript">
        function calc() {
            const amount = document.forms["currencyform"]["amount"].value;
            const elementFrom = document.forms["currencyform"]["from"];
            const from=elementFrom.value;
            const fromText = elementFrom.options[elementFrom.selectedIndex].text;
            const elementTo = document.forms["currencyform"]["to"];
            const to=elementTo.value;
            const toText = elementTo.options[elementTo.selectedIndex].text;
            if(isNaN(amount) || amount === "") {
                alert("Invalid input data given");
            } else {
                let result = (amount / from) * to;
                let rate = (to/from);
                result = result.toFixed(2);
                rate = rate.toFixed(2);
                const resultData = amount + " " + fromText + " converted to " + toText +
                    " with rate " + rate + " equals " + result;
                document.getElementById('data').innerHTML = resultData;
            }
        }
    </script>
</head>
<body onload='document.currencyform.amount.focus()'>
<div id="page">
    <div id="content" class="center">
        <table>
            <caption>Converter service. All currencies quoted against the euro (base currency)</caption>
            <form name="currencyform" id="currencyform" onsubmit="calc()" action="">
                <tr>
                    <td>
                        <label for="amount">Amount:</label>
                        <input type="text" id="amount" name="amount"><br><br>
                    </td>
                    <td class="center">
                        <label for="from">From:</label>
                        <select name="from" id="from">
                            <c:forEach items="${currencies}" var="currency">
                                <option value="${currency.rate}">${currency.currency}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="center">
                        <label for="to">To:</label>
                        <select name="to" id="to">
                            <c:forEach items="${currencies}" var="currency">
                                <option value="${currency.rate}">${currency.currency}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input id="calculate" type="button" value="calculate" onclick="calc();" />
                    </td>
                </tr>
            </form>
        </table>
    </div>
    <div id="result" >
        <p id="data" name="data"></p>
    </div>
</div>
</body>
</html>