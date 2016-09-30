/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception.shared;

/**
 *
 * @author Dave
 */
public class ResourceNotExistingException extends Exception {

    /**
     * Creates a new instance of <code>ResourceNotExistingException</code>
     * without detail message.
     */
    public ResourceNotExistingException() {
    }

    /**
     * Constructs an instance of <code>ResourceNotExistingException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ResourceNotExistingException(String msg) {
        super(msg);
    }
}
