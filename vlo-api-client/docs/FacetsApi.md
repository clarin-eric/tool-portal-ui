# FacetsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getFacet**](FacetsApi.md#getFacet) | **GET** /facets/{facetName} | Get the facets and their (top) values and their counts |
| [**getFacets**](FacetsApi.md#getFacets) | **GET** /facets | Get the facets and their (top) values and their counts |



## getFacet

> Facet getFacet(facetName, q, fq)

Get the facets and their (top) values and their counts

### Example

```java
// Import classes:
import eu.clarin.cmdi.vlo.openapi.client.ApiClient;
import eu.clarin.cmdi.vlo.openapi.client.ApiException;
import eu.clarin.cmdi.vlo.openapi.client.Configuration;
import eu.clarin.cmdi.vlo.openapi.client.models.*;
import eu.clarin.cmdi.vlo.openapi.client.api.FacetsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        FacetsApi apiInstance = new FacetsApi(defaultClient);
        String facetName = "facetName_example"; // String | 
        String q = "*:*"; // String | 
        List<String> fq = Arrays.asList(); // List<String> | 
        try {
            Facet result = apiInstance.getFacet(facetName, q, fq);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling FacetsApi#getFacet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **facetName** | **String**|  | |
| **q** | **String**|  | [optional] [default to *:*] |
| **fq** | [**List&lt;String&gt;**](String.md)|  | [optional] |

### Return type

[**Facet**](Facet.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **500** | Internal Server Error |  -  |
| **200** | OK |  -  |


## getFacets

> List&lt;Facet&gt; getFacets(q, fq, fields)

Get the facets and their (top) values and their counts

### Example

```java
// Import classes:
import eu.clarin.cmdi.vlo.openapi.client.ApiClient;
import eu.clarin.cmdi.vlo.openapi.client.ApiException;
import eu.clarin.cmdi.vlo.openapi.client.Configuration;
import eu.clarin.cmdi.vlo.openapi.client.models.*;
import eu.clarin.cmdi.vlo.openapi.client.api.FacetsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        FacetsApi apiInstance = new FacetsApi(defaultClient);
        String q = "*:*"; // String | 
        List<String> fq = Arrays.asList(); // List<String> | 
        List<String> fields = Arrays.asList(); // List<String> | 
        try {
            List<Facet> result = apiInstance.getFacets(q, fq, fields);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling FacetsApi#getFacets");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **q** | **String**|  | [optional] [default to *:*] |
| **fq** | [**List&lt;String&gt;**](String.md)|  | [optional] |
| **fields** | [**List&lt;String&gt;**](String.md)|  | [optional] |

### Return type

[**List&lt;Facet&gt;**](Facet.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **500** | Internal Server Error |  -  |
| **200** | OK |  -  |

