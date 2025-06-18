// IAnalyticsInterface.aidl
package com.gabriel.analyticsinterface;

import com.gabriel.analyticsinterface.AnalyticsStats;

interface IAnalyticsInterface {
    AnalyticsStats getCurrentStats();
}