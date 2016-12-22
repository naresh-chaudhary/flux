/*
 * Copyright 2012-2016, the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.flux.guice.module;

import com.flipkart.flux.deploymentunit.DeploymentUnitUtil;
import com.flipkart.flux.deploymentunit.DirectoryBasedDeploymentUnitUtil;
import com.flipkart.flux.deploymentunit.NoOpDeploymentUnitUtil;
import com.google.inject.Provider;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * <code>DeploymentUnitUtilProvider</code> binds and provides {@link DeploymentUnitUtil} based on configuration.
 */
public class DeploymentUnitUtilProvider implements Provider<DeploymentUnitUtil> {

    private DeploymentUnitUtil deploymentUnitUtil;

    @Inject
    public DeploymentUnitUtilProvider(@Named("deploymentType") String deploymentType, @Named("deploymentUnitsPath") String deploymentUnitsPath ) {
        if("directory".equals(deploymentType)) {
            deploymentUnitUtil = new DirectoryBasedDeploymentUnitUtil(deploymentUnitsPath);
        } else {
            deploymentUnitUtil = new NoOpDeploymentUnitUtil();
        }
    }

    @Override
    public DeploymentUnitUtil get() {
        return deploymentUnitUtil;
    }
}
