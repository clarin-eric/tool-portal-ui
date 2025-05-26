# RecordsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getRecord**](RecordsApi.md#getRecord) | **GET** /records/{id} | Retrieve an individual record by its identifier |
| [**getRecords**](RecordsApi.md#getRecords) | **GET** /records | Retrieve one or more records by query and/or filtered |
| [**getRecordsCount**](RecordsApi.md#getRecordsCount) | **GET** /records/count | Retrieve the exact number of records that can be retrieved by query and/or filtered as JSON object with a single property &#39;numFound&#39; |
| [**saveRecord**](RecordsApi.md#saveRecord) | **POST** /records | Submit a new record |



## getRecord

> VloRecord getRecord(id)

Retrieve an individual record by its identifier

### Example

```java
// Import classes:
import eu.clarin.cmdi.vlo.openapi.client.ApiClient;
import eu.clarin.cmdi.vlo.openapi.client.ApiException;
import eu.clarin.cmdi.vlo.openapi.client.Configuration;
import eu.clarin.cmdi.vlo.openapi.client.models.*;
import eu.clarin.cmdi.vlo.openapi.client.api.RecordsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        RecordsApi apiInstance = new RecordsApi(defaultClient);
        String id = "id_example"; // String | 
        try {
            VloRecord result = apiInstance.getRecord(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling RecordsApi#getRecord");
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
| **id** | **String**|  | |

### Return type

[**VloRecord**](VloRecord.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **500** | Internal Server Error |  -  |
| **200** | A record with the specified identifier that is present in the catalogue |  -  |
| **404** | No record with the specified identifier is present in the catalogue |  -  |


## getRecords

> VloRecordSearchResult getRecords(q, fq, from, size)

Retrieve one or more records by query and/or filtered

### Example

```java
// Import classes:
import eu.clarin.cmdi.vlo.openapi.client.ApiClient;
import eu.clarin.cmdi.vlo.openapi.client.ApiException;
import eu.clarin.cmdi.vlo.openapi.client.Configuration;
import eu.clarin.cmdi.vlo.openapi.client.models.*;
import eu.clarin.cmdi.vlo.openapi.client.api.RecordsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        RecordsApi apiInstance = new RecordsApi(defaultClient);
        String q = "*:*"; // String | 
        List<String> fq = Arrays.asList(); // List<String> | 
        Integer from = 0; // Integer | 
        Integer size = 10; // Integer | 
        try {
            VloRecordSearchResult result = apiInstance.getRecords(q, fq, from, size);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling RecordsApi#getRecords");
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
| **from** | **Integer**|  | [optional] [default to 0] |
| **size** | **Integer**|  | [optional] [default to 10] |

### Return type

[**VloRecordSearchResult**](VloRecordSearchResult.md)

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


## getRecordsCount

> Map&lt;String, Object&gt; getRecordsCount(query, fq)

Retrieve the exact number of records that can be retrieved by query and/or filtered as JSON object with a single property &#39;numFound&#39;

### Example

```java
// Import classes:
import eu.clarin.cmdi.vlo.openapi.client.ApiClient;
import eu.clarin.cmdi.vlo.openapi.client.ApiException;
import eu.clarin.cmdi.vlo.openapi.client.Configuration;
import eu.clarin.cmdi.vlo.openapi.client.models.*;
import eu.clarin.cmdi.vlo.openapi.client.api.RecordsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        RecordsApi apiInstance = new RecordsApi(defaultClient);
        String query = "*:*"; // String | 
        List<String> fq = Arrays.asList(); // List<String> | 
        try {
            Map<String, Object> result = apiInstance.getRecordsCount(query, fq);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling RecordsApi#getRecordsCount");
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
| **query** | **String**|  | [optional] [default to *:*] |
| **fq** | [**List&lt;String&gt;**](String.md)|  | [optional] |

### Return type

**Map&lt;String, Object&gt;**

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


## saveRecord

> VloRecord saveRecord(vloRecord)

Submit a new record

### Example

```java
// Import classes:
import eu.clarin.cmdi.vlo.openapi.client.ApiClient;
import eu.clarin.cmdi.vlo.openapi.client.ApiException;
import eu.clarin.cmdi.vlo.openapi.client.Configuration;
import eu.clarin.cmdi.vlo.openapi.client.models.*;
import eu.clarin.cmdi.vlo.openapi.client.api.RecordsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        RecordsApi apiInstance = new RecordsApi(defaultClient);
        VloRecord vloRecord = new VloRecord(); // VloRecord | 
        try {
            VloRecord result = apiInstance.saveRecord(vloRecord);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling RecordsApi#saveRecord");
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
| **vloRecord** | [**VloRecord**](VloRecord.md)|  | |

### Return type

[**VloRecord**](VloRecord.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **500** | Internal Server Error |  -  |
| **200** | OK |  -  |

