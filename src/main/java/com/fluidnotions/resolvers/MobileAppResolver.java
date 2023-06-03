package com.fluidnotions.resolvers;

import com.fluidnotions.databases.fake.FakeMobileAppDataSource;
import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.MobileApp;
import com.fluidnotions.graphql.generated.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@DgsComponent
public class MobileAppResolver {
    private static final Logger logger = LoggerFactory.getLogger(MobileAppResolver.class);

    //    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "mobileApps")
    public List<MobileApp> mobileAppsCustom(@InputArgument Optional<MobileAppFilter> mobileAppFilter) {
        var mobileApps = FakeMobileAppDataSource.MOBILE_APP_LIST;
        if (mobileAppFilter.isEmpty()) {
            return mobileApps;
        }
        var filterProps = this.classToMapEntries(mobileAppFilter.get());
        var filtered = mobileApps.stream().filter(app -> {
            var providedProps = filterProps.stream().filter(p -> p != null);
            var allMatch = providedProps.allMatch(prop -> {
                try {
                    var field = app.getClass().getDeclaredField(prop.getKey());
                    field.setAccessible(true);
                    var value = field.get(app);
                    var match = value.equals(prop.getValue());
                    return match;

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.error("Error filtering mobile apps", e);
                    return false;
                }
            });
            return allMatch;
        }).collect(Collectors.toList());
        return filtered;
    }

    public <T> List<Map.Entry<String, Object>> classToMapEntries(T obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        // Create a Map.Entry object for each field-value pair
                        return new AbstractMap.SimpleEntry<>(field.getName(), field.get(obj));
                    } catch (IllegalAccessException e) {
                        // Handle any errors that occur while getting the field value
                        return null;
                    }
                })
                .filter(entry -> entry != null)
                .collect(Collectors.toList());
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.MobileApps
    )
    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter")
                                         Optional<MobileAppFilter> filter) {
        if (filter.isEmpty()) {
            return FakeMobileAppDataSource.MOBILE_APP_LIST;
        }

        return FakeMobileAppDataSource.MOBILE_APP_LIST.stream().filter(
                mobileApp -> this.matchFilter(filter.get(), mobileApp)
        ).collect(Collectors.toList());
    }

    private boolean matchFilter(MobileAppFilter mobileAppFilter, MobileApp mobileApp) {
        var isAppMatch = StringUtils.containsIgnoreCase(mobileApp.getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getName(), StringUtils.EMPTY))
                && StringUtils.containsIgnoreCase(mobileApp.getVersion(),
                StringUtils.defaultIfBlank(mobileAppFilter.getVersion(), StringUtils.EMPTY))
                && mobileApp.getReleaseDate().isAfter(
                Optional.ofNullable(mobileAppFilter.getReleasedAfter()).orElse(LocalDate.MIN))
                && mobileApp.getDownloaded() >=
                Optional.ofNullable(mobileAppFilter.getMinimumDownload()).orElse(0);

        if (!isAppMatch) {
            return false;
        }

        if (StringUtils.isNotBlank(mobileAppFilter.getPlatform())
                && !mobileApp.getPlatform().contains(mobileAppFilter.getPlatform().toLowerCase())) {
            return false;
        }

        if (mobileAppFilter.getAuthor() != null
                && !StringUtils.containsIgnoreCase(mobileApp.getAuthor().getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getAuthor().getName(), StringUtils.EMPTY))) {
            return false;
        }

        return true;
    }

}
