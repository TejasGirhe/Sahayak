package org.tensorflow.lite.examples.objectdetection.classes;

import java.util.HashMap;
import java.util.Locale;

public class Models3D {
    HashMap<String, String> hash_map = new HashMap<String, String>();
    String[] models = {"avocado", "boat", "cub", "flower", "kettle", "key", "lion", "lock", "quartz", "ring", "submarine", "television", "tiger", "tree", "truck", "umbrella", "urn", "vase", "violin", "zebra"};

    public HashMap<String, String> getModels(){
        for (String s : models) {
            String model = s.toLowerCase(Locale.ROOT);
            String url = "https://raw.githubusercontent.com/TejasGirhe/3D-Models/main/" + model + "/scene.gltf";
            hash_map.put(model, url);
        }
        return hash_map;
    }
}
