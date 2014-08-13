
package dev.sfilizzola.utils;

import java.util.EventObject;

public class ArrayListChangedEvent extends EventObject {

    private static final long serialVersionUID = 1L;
    private ArrayListChangedEventType operation;
    
    
    public ArrayListChangedEvent(Object source, ArrayListChangedEventType operation) {

        super(source);
        this.operation = operation;
    }

    public ArrayListChangedEventType getOperation() {

        return operation;
    }

}
