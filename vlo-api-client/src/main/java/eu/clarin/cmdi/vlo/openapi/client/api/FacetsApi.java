package eu.clarin.cmdi.vlo.openapi.client.api;

import eu.clarin.cmdi.vlo.openapi.client.ApiClient;

import eu.clarin.cmdi.vlo.openapi.client.model.Facet;

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

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-30T18:42:32.083747+01:00[Europe/Amsterdam]", comments = "Generator version: 7.14.0")
public class FacetsApi {
    private ApiClient apiClient;

    public FacetsApi() {
        this(new ApiClient());
    }

    public FacetsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param facetName The facetName parameter
     * @param q The q parameter
     * @param fq The fq parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return Facet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getFacetRequestCreation(@jakarta.annotation.Nonnull String facetName, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        Object postBody = null;
        // verify the required parameter 'facetName' is set
        if (facetName == null) {
            throw new RestClientResponseException("Missing the required parameter 'facetName' when calling getFacet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("facetName", facetName);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "fq", fq));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "valueCountLimit", valueCountLimit));
        
        final String[] localVarAccepts = { 
            "*/*", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Facet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/facets/{facetName}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param facetName The facetName parameter
     * @param q The q parameter
     * @param fq The fq parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return Facet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Facet getFacet(@jakarta.annotation.Nonnull String facetName, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        ParameterizedTypeReference<Facet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFacetRequestCreation(facetName, q, fq, valueCountLimit).body(localVarReturnType);
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param facetName The facetName parameter
     * @param q The q parameter
     * @param fq The fq parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return ResponseEntity&lt;Facet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Facet> getFacetWithHttpInfo(@jakarta.annotation.Nonnull String facetName, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        ParameterizedTypeReference<Facet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFacetRequestCreation(facetName, q, fq, valueCountLimit).toEntity(localVarReturnType);
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param facetName The facetName parameter
     * @param q The q parameter
     * @param fq The fq parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getFacetWithResponseSpec(@jakarta.annotation.Nonnull String facetName, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        return getFacetRequestCreation(facetName, q, fq, valueCountLimit);
    }
    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param fields The fields parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return List&lt;Facet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getFacetsRequestCreation(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "fq", fq));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "fields", fields));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "valueCountLimit", valueCountLimit));
        
        final String[] localVarAccepts = { 
            "*/*", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<List<Facet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/facets", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param fields The fields parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return List&lt;Facet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<Facet> getFacets(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        ParameterizedTypeReference<List<Facet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFacetsRequestCreation(q, fq, fields, valueCountLimit).body(localVarReturnType);
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param fields The fields parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return ResponseEntity&lt;List&lt;Facet&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Facet>> getFacetsWithHttpInfo(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        ParameterizedTypeReference<List<Facet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFacetsRequestCreation(q, fq, fields, valueCountLimit).toEntity(localVarReturnType);
    }

    /**
     * Get the facets and their (top) values and their counts
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - OK
     * @param q The q parameter
     * @param fq The fq parameter
     * @param fields The fields parameter
     * @param valueCountLimit The valueCountLimit parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getFacetsWithResponseSpec(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable List<String> fq, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer valueCountLimit) throws RestClientResponseException {
        return getFacetsRequestCreation(q, fq, fields, valueCountLimit);
    }
}
