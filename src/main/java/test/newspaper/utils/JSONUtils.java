package test.newspaper.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    /**
     * Recursively finds all values matching a specific key in the JSON tree.
     * Optionally filters JSONObject arrays by a nested key-value pair.
     *
     * @param json        the root JSONObject
     * @param targetKey   the key to search for
     * @param filterKey   (optional) key to filter individual JSONObject entries
     * @param filterValue (optional) value to match for filtering
     * @return List of matching objects (filtered if applicable)
     */
    public static List<JSONObject> findAllMatchingObjects(JSONObject json, String targetKey, String filterKey, String filterValue) {
        List<JSONObject> results = new ArrayList<>();
        recursiveSearch(json, targetKey, filterKey, filterValue, results);
        return results;
    }

    private static void recursiveSearch(Object current, String targetKey, String filterKey, String filterValue, List<JSONObject> results) {
        if (current instanceof JSONObject) {
            JSONObject obj = (JSONObject) current;
            for (String key : obj.keySet()) {
                Object value = obj.get(key);

                if (key.equals(targetKey)) {
                    if (value instanceof JSONArray) {
                        JSONArray arr = (JSONArray) value;
                        for (int i = 0; i < arr.length(); i++) {
                            Object item = arr.get(i);
                            if (item instanceof JSONObject) {
                                JSONObject itemObj = (JSONObject) item;
                                if (filterKey == null || (itemObj.has(filterKey) && itemObj.getString(filterKey).equals(filterValue))) {
                                    results.add(itemObj);
                                }
                            }
                        }
                    } else if (value instanceof JSONObject) {
                        JSONObject itemObj = (JSONObject) value;
                        if (filterKey == null || (itemObj.has(filterKey) && itemObj.getString(filterKey).equals(filterValue))) {
                            results.add(itemObj);
                        }
                    }
                }

                recursiveSearch(value, targetKey, filterKey, filterValue, results);
            }
        } else if (current instanceof JSONArray) {
            JSONArray array = (JSONArray) current;
            for (int i = 0; i < array.length(); i++) {
                recursiveSearch(array.get(i), targetKey, filterKey, filterValue, results);
            }
        }
    }
}
