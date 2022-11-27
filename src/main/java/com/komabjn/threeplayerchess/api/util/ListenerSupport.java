package com.komabjn.threeplayerchess.api.util;

/**
 *
 * @author Komabjn
 */
public interface ListenerSupport<TYPE> {

    public void registerListener(TYPE listener);
    
    public void unregisterListener(TYPE listener);
    
}
