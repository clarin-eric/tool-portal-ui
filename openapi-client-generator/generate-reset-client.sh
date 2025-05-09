#!/usr/bin/env bash

export OPENAPI_GENERATOR_VERSION=7.8.0

BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

"${BASE_DIR}/openapi-generator-cli" generate \
  -i http://petstore.swagger.io/v2/swagger.json \
  --api-package com.baeldung.petstore.client.api \
  --model-package com.baeldung.petstore.client.model \
  --invoker-package com.baeldung.petstore.client.invoker \
  --group-id com.baeldung \
  --artifact-id spring-openapi-generator-api-client \
  --artifact-version 0.0.1-SNAPSHOT \
  -g java \
  -p java8=true \
  --library resttemplate \
  -o spring-openapi-generator-api-client