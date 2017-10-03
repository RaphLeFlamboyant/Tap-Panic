package com.flamboyant.tappanic.tools.assetpack;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Reborn Portable on 10/02/2016.
 */
public class AssetPack {
    private ObjectMap<String, String> assetPathList = new ObjectMap<String, String>();

    public void add(String key, String path)
    {
        assetPathList.put(key, path);
    }

    public ObjectMap<String, String> getAssetPathList() {
        return assetPathList;
    }
}
