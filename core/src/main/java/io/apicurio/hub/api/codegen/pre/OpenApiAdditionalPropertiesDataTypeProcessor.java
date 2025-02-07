/*
 * Copyright 2021 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.hub.api.codegen.pre;

import io.apicurio.datamodels.combined.visitors.CombinedVisitorAdapter;
import io.apicurio.datamodels.core.models.common.IDefinition;
import io.apicurio.datamodels.openapi.models.OasSchema;

/**
 * @author eric.wittmann@gmail.com
 */
public class OpenApiAdditionalPropertiesDataTypeProcessor extends CombinedVisitorAdapter {

    /**
     * @see CombinedVisitorAdapter#visitSchemaDefinition(IDefinition)
     */
    @Override
    public void visitSchemaDefinition(IDefinition node) {
        OasSchema schema = (OasSchema) node;
        if (schema.hasAdditionalPropertiesBoolean()) {
            schema.additionalProperties = Boolean.TRUE;
        } else if (schema.hasAdditionalPropertiesSchema()) {
            // TODO properly support the typed schema
            schema.additionalProperties = Boolean.TRUE;
        } else {
            schema.additionalProperties = Boolean.FALSE;
        }
    }
}
