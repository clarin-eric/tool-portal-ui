package eu.clarin.cmdi.vlo.openapi.client.api;

import eu.clarin.cmdi.vlo.openapi.client.ApiClient;

import eu.clarin.cmdi.vlo.openapi.client.model.VloRecord;
import eu.clarin.cmdi.vlo.openapi.client.model.VloRecordSearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.client.RestClientResponseException;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-07-17T17:34:13.589077+03:00[Europe/Riga]", comments = "Generator version: 7.14.0")
public class RecordsApi {
    private ApiClient apiClient;

    public RecordsApi() {
        this(new ApiClient());
    }

    public RecordsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve an individual record by its identifier
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - A record with the specified identifier that is present in the catalogue
     * <p><b>404</b> - No record with the specified identifier is present in the catalogue
     * @param id The id parameter
     * @return VloRecord
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRecordRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "*/*", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<VloRecord> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/records/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an individual record by its identifier
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - A record with the specified identifier that is present in the catalogue
     * <p><b>404</b> - No record with the specified identifier is present in the catalogue
     * @param id The id parameter
     * @return VloRecord
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VloRecord getRecord(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<VloRecord> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecordRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve an individual record by its identifier
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - A record with the specified identifier that is present in the catalogue
     * <p><b>404</b> - No record with the specified identifier is present in the catalogue
     * @param id The id parameter
     * @return ResponseEntity&lt;VloRecord&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VloRecord> getRecordWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<VloRecord> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecordRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an individual record by its identifier
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - A record with the specified identifier that is present in the catalogue
     * <p><b>404</b> - No record with the specified identifier is present in the catalogue
     * @param id The id parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRecordWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getRecordRequestCreation(id);
    }
    /**
     * Retrieve one or more records by query and/or filtered
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param from The from parameter
     * @param size The size parameter
     * @return VloRecordSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRecordsRequestCreation(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer from, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "fq", fq));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));
        
        final String[] localVarAccepts = { 
            "*/*", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<VloRecordSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/records", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve one or more records by query and/or filtered
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param from The from parameter
     * @param size The size parameter
     * @return VloRecordSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VloRecordSearchResult getRecords(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer from, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        ParameterizedTypeReference<VloRecordSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecordsRequestCreation(q, fq, from, size).body(localVarReturnType);
    }

    /**
     * Retrieve one or more records by query and/or filtered
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param from The from parameter
     * @param size The size parameter
     * @return ResponseEntity&lt;VloRecordSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VloRecordSearchResult> getRecordsWithHttpInfo(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer from, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        ParameterizedTypeReference<VloRecordSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecordsRequestCreation(q, fq, from, size).toEntity(localVarReturnType);
    }

    /**
     * Retrieve one or more records by query and/or filtered
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param from The from parameter
     * @param size The size parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRecordsWithResponseSpec(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer from, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        return getRecordsRequestCreation(q, fq, from, size);
    }
    /**
     * Retrieve the exact number of records that can be retrieved by query and/or filtered as JSON object with a single property &#39;numFound&#39;
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param query The query parameter
     * @param fq The fq parameter
     * @return Map&lt;String, Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRecordsCountRequestCreation(@jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<String> fq) throws RestClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "fq", fq));
        
        final String[] localVarAccepts = { 
            "*/*", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Map<String, Object>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/records/count", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the exact number of records that can be retrieved by query and/or filtered as JSON object with a single property &#39;numFound&#39;
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param query The query parameter
     * @param fq The fq parameter
     * @return Map&lt;String, Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Map<String, Object> getRecordsCount(@jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<String> fq) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Object>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecordsCountRequestCreation(query, fq).body(localVarReturnType);
    }

    /**
     * Retrieve the exact number of records that can be retrieved by query and/or filtered as JSON object with a single property &#39;numFound&#39;
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param query The query parameter
     * @param fq The fq parameter
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Object>> getRecordsCountWithHttpInfo(@jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<String> fq) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Object>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecordsCountRequestCreation(query, fq).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the exact number of records that can be retrieved by query and/or filtered as JSON object with a single property &#39;numFound&#39;
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param query The query parameter
     * @param fq The fq parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRecordsCountWithResponseSpec(@jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<String> fq) throws RestClientResponseException {
        return getRecordsCountRequestCreation(query, fq);
    }
    /**
     * Submit a new record
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param vloRecord The vloRecord parameter
     * @return VloRecord
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec saveRecordRequestCreation(@jakarta.annotation.Nonnull VloRecord vloRecord) throws RestClientResponseException {
        Object postBody = vloRecord;
        // verify the required parameter 'vloRecord' is set
        if (vloRecord == null) {
            throw new RestClientResponseException("Missing the required parameter 'vloRecord' when calling saveRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "*/*", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<VloRecord> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/records", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Submit a new record
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param vloRecord The vloRecord parameter
     * @return VloRecord
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VloRecord saveRecord(@jakarta.annotation.Nonnull VloRecord vloRecord) throws RestClientResponseException {
        ParameterizedTypeReference<VloRecord> localVarReturnType = new ParameterizedTypeReference<>() {};
        return saveRecordRequestCreation(vloRecord).body(localVarReturnType);
    }

    /**
     * Submit a new record
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param vloRecord The vloRecord parameter
     * @return ResponseEntity&lt;VloRecord&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VloRecord> saveRecordWithHttpInfo(@jakarta.annotation.Nonnull VloRecord vloRecord) throws RestClientResponseException {
        ParameterizedTypeReference<VloRecord> localVarReturnType = new ParameterizedTypeReference<>() {};
        return saveRecordRequestCreation(vloRecord).toEntity(localVarReturnType);
    }

    /**
     * Submit a new record
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param vloRecord The vloRecord parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec saveRecordWithResponseSpec(@jakarta.annotation.Nonnull VloRecord vloRecord) throws RestClientResponseException {
        return saveRecordRequestCreation(vloRecord);
    }
}
