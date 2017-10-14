package dualcoresimulator;

/**
 * This class reports Heap exceptions.
 * @author Duncan
 * @since 09-24-2017
 */
public class HeapException extends Exception 
{
    /**
     * Creates a new instance of <code>HeapException</code> without detail
     * message.
     */
    public HeapException() 
	{
    }
    /**
     * Constructs an instance of <code>HeapException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public HeapException(String msg) 
	{
        super(msg);
    }
}
