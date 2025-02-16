/*
 * Copyright Chris2018998
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.beeop;

/**
 * Bee Object SourceConfig JMX Bean interface
 *
 * @author Chris.Liao
 * @version 1.0
 */

public interface BeeObjectSourceConfigJmxBean {

    String getPoolName();

    boolean isFairMode();

    int getInitialSize();

    int getMaxActive();

    int getBorrowSemaphoreSize();

    long getMaxWait();

    long getIdleTimeout();

    long getHoldTimeout();

    int getObjectTestTimeout();

    long getObjectTestInterval();

    boolean isForceCloseUsingOnClear();

    long getDelayTimeForNextClear();

    long getIdleCheckTimeInterval();

    String getPoolImplementClassName();

    String getObjectFactoryClassName();

    boolean isEnableJmx();
}
