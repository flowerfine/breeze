package cn.sliew.scaleph.system.cache;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import cn.hutool.core.util.ObjectUtil;
import cn.sliew.scaleph.cache.CaffeineCacheConfig;
import cn.sliew.scaleph.system.service.DictTypeService;
import cn.sliew.scaleph.system.service.dto.DictTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * @author gleiyu
 */
@Slf4j
@Component
public class DictTypeCache {

    private static Cache dictTypeCache;

    @Autowired
    @Qualifier(value = "unBoundedCacheManager")
    private CacheManager cacheManager;

    @Autowired
    private DictTypeService dictTypeService;

    public DictTypeCache() {

    }

    public synchronized static void updateCache(List<DictTypeDTO> list) {
        for (DictTypeDTO dictType : list) {
            updateCache(dictType);
        }
    }

    public synchronized static void updateCache(DictTypeDTO dictType) {
        if (ObjectUtil.isNotNull(dictType)) {
            dictTypeCache.put(dictType.getDictTypeCode(), dictType.getDictTypeName());
        }
    }

    public synchronized static void evictCache(String key) {
        dictTypeCache.evict(key);
    }

    public static String getValueByKey(String key) {
        return dictTypeCache.get(key, String.class);
    }

    @SuppressWarnings("unchecked")
    public static List<DictTypeDTO> listAll() {
        List<DictTypeDTO> list = new ArrayList<>();
        com.github.benmanes.caffeine.cache.Cache<String, String> localCache =
            (com.github.benmanes.caffeine.cache.Cache<String, String>) dictTypeCache.getNativeCache();
        ConcurrentMap<String, String> map = localCache.asMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            DictTypeDTO dictType = new DictTypeDTO();
            dictType.setDictTypeCode(entry.getKey());
            dictType.setDictTypeName(entry.getValue());
            list.add(dictType);
        }
        return list;
    }

    @PostConstruct
    public synchronized void init() {
        log.info("initializing cache " + CaffeineCacheConfig.UnBoundedCaches.CACHE_DICT_TYPE);
        dictTypeCache = cacheManager.getCache(CaffeineCacheConfig.UnBoundedCaches.CACHE_DICT_TYPE);
        List<DictTypeDTO> list = this.dictTypeService.selectAll();
        dictTypeCache.clear();
        updateCache(list);
    }
}
