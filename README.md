# Getting Started

Exchange Rate Service API


1)Api to fetch ECB reference rates for EUR and Non EUR currrency pair

http://localhost:8080/api/exchangerate?currencyPair=USD/EUR

Response : 
{
    "baseCurrency": "EUR",
    "rate": {
        "exchangeRate": {
            "USD": "1.1345"
        }
    }
}


http://localhost:8080/api/exchangerate?currencyPair=HUF/USD

Response :

{
    "baseCurrency": "USD",
    "rate": {
        "exchangeRate": {
            "HUF": "313.68884"
        }
    }
}


2)Api to get list of supported currencies and number of request count

http://localhost:8080/api/supportedcurrencies

Response:

{
    "supportedCurrency": [
        {
            "currency": "USD",
            "numberOfRequests": 1
        },
        {
            "currency": "JPY",
            "numberOfRequests": 0
        },
        {
            "currency": "BGN",
            "numberOfRequests": 0
        },
        {
            "currency": "CZK",
            "numberOfRequests": 0
        },
        {
            "currency": "DKK",
            "numberOfRequests": 0
        },
        {
            "currency": "GBP",
            "numberOfRequests": 0
        },
        {
            "currency": "HUF",
            "numberOfRequests": 0
        },
        {
            "currency": "PLN",
            "numberOfRequests": 0
        },
        {
            "currency": "RON",
            "numberOfRequests": 0
        },
        {
            "currency": "SEK",
            "numberOfRequests": 0
        },
        {
            "currency": "CHF",
            "numberOfRequests": 0
        },
        {
            "currency": "ISK",
            "numberOfRequests": 0
        },
        {
            "currency": "NOK",
            "numberOfRequests": 0
        },
        {
            "currency": "HRK",
            "numberOfRequests": 0
        },
        {
            "currency": "RUB",
            "numberOfRequests": 0
        },
        {
            "currency": "TRY",
            "numberOfRequests": 0
        },
        {
            "currency": "AUD",
            "numberOfRequests": 0
        },
        {
            "currency": "BRL",
            "numberOfRequests": 0
        },
        {
            "currency": "CAD",
            "numberOfRequests": 0
        },
        {
            "currency": "CNY",
            "numberOfRequests": 0
        },
        {
            "currency": "HKD",
            "numberOfRequests": 0
        },
        {
            "currency": "IDR",
            "numberOfRequests": 0
        },
        {
            "currency": "ILS",
            "numberOfRequests": 0
        },
        {
            "currency": "INR",
            "numberOfRequests": 0
        },
        {
            "currency": "KRW",
            "numberOfRequests": 0
        },
        {
            "currency": "MXN",
            "numberOfRequests": 0
        },
        {
            "currency": "MYR",
            "numberOfRequests": 0
        },
        {
            "currency": "NZD",
            "numberOfRequests": 0
        },
        {
            "currency": "PHP",
            "numberOfRequests": 0
        },
        {
            "currency": "SGD",
            "numberOfRequests": 0
        },
        {
            "currency": "THB",
            "numberOfRequests": 0
        },
        {
            "currency": "ZAR",
            "numberOfRequests": 0
        }
    ]
}


3)Api to convert an given amount from one currency to another

http://localhost:8080/api/currencyconvertor?amount=15&currency=EUR&conversionCurrency=GBP

Response:

{
    "convertedRate": "12.4752 GBP"
}
 
