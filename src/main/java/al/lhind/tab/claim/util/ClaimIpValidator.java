package al.lhind.tab.claim.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ClaimIpValidator {
    private static final Map<String, List<LocalDateTime>> cache = new ConcurrentHashMap<>();
    //Hardcoded for now #wip
    private static final int MAX_RETRY_CLAIMS = 5;
    private static final int MAX_RETRY_MINUTES = 1;

    public static boolean validate(String ip) {
        LocalDateTime now = LocalDateTime.now();
        if (cache.containsKey(ip)) {
            List<LocalDateTime> dates = cache.get(ip);
            long minutes = Duration.between(dates.get(0), now).toMinutes();
            log.info("Difference between dates is {}", minutes);

            if (minutes >= MAX_RETRY_MINUTES) {
                log.info("Minutes is more than limit");
                dates.remove(0);
                dates.add(now);
            } else if (MAX_RETRY_CLAIMS > dates.size()) {
                log.info("Retry count is more than limit");
                dates.add(now);
            } else {
                return false;
            }
        } else {
            cache.put(ip, new ArrayList<>(List.of(LocalDateTime.now())));
        }

        return true;
    }
}
