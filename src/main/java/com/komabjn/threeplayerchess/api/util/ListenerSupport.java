package com.komabjn.threeplayerchess.api.util;

/**
 *
 * @author Komabjn
 * @param <TYPE>
 */
public interface ListenerSupport<TYPE> {

    public void registerListener(TYPE listener);
    
    public void unregisterListener(TYPE listener);
    
}
