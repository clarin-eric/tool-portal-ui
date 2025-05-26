#!/usr/bin/env bash
API_DOCS_URL="${API_DOCS_URL:-http://localhost:8080/v3/api-docs}"

curl -o 'spec/openapi.json' "${API_DOCS_URL}" \
	&& mvn -f openapi-client-generator \
		initialize -DskipTests=true \
	&& git checkout -- vlo-api-client/pom.xml \
	&& mvn -f vlo-api-client \
		clean install
