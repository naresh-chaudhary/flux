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

package com.flipkart.flux.constant;

/**
 * The <code>RuntimeConstants</code> class is a placeholder for all runtime framework constants.
 *
 * @author regunath.balasubramanian
 * @author kartik.bommepally
 */
public class RuntimeConstants {

    /**
     * Useful constants for servlet container configuration parts
     */
    public static final String DASHBOARD_CONTEXT_PATH = "/admin";
    public static final String API_CONTEXT_PATH = "/api";

    /***
     * default eventSource for replay event
     */
    public static final String REPLAY_EVENT = "flux_runtime_replay_internal";

    /**
     * Root for dashboard webapp configs.
     */
    public static final String DASHBOARD_VIEW = "dashboard";
    public static final String FSM_VIEW = "fsmview";
    public static final String RESOURCE_NOT_AVAILABLE_VIEW = "resourcenotavailable";

    public static final String STATE_MACHINE_RESOURCE_RELATIVE_PATH = "/machines";

    /**
     * default timeout for a task
     */
    public static final Long defaultTaskTimeout = 1000l;

    /**
     * default value for clientElbId in StateMachineDefinition
     */
    public static final String DEFAULT_ELB_ID = "defaultElbId";

    public static final Short MAX_REPLAYABLE_RETRIES = 5;

    /**
     * Default message for initialised replayable traversal pat states. Execution Version for
     * dependent events in replayable traversal path is computed at runtime based on valid
     * available dependent event.
     */
    public static final String DEFAULT_DEPENDENT_EVENTS_MESSAGE = "Computed at Runtime";

    public static final String DEPENDENT_EVENTS_MESSAGE_TASK_CANCELLATION = "Not applicable";

    public static final int ERROR_MSG_LENGTH_IN_AUDIT = 995;
}