package com.miravalles;

/**
 * Un Predicate pero que puede lanzar cualquier execpción para no tener que manejarlas con try como en Groovy
 */
public  interface Predicado<T> {

    boolean call(T valor) throws Exception;
}
