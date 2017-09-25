/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elko.cachedemo;

/**
 *
 * @author elko
 */
public class document_producer {
    public static String getDocument(String request) {
            String[] parts = request.split(" ");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int seed = request.length()+parts.length;
            while( sb.length() < 1000 ) {
                seed = ( seed * 65793 + 4282663 ) % (1<<23);
                if( ( ( seed >> 8 ) & 1 ) == 0 ) sb.append( " " );
                else {
                    seed = ( seed * 65793 + 4282663 ) % (1<<23);
                    sb.append( parts[ ( ( seed >> 8 ) & 65535 ) % parts.length ] );
                }
            }
            return sb.toString();
        }
    
    
}
