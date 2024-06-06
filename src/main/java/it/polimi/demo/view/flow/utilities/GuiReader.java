package it.polimi.demo.view.flow.utilities;

/**
 * InputReaderGUI class
 * InputReaderGUI is the class that reads the input and add it to the buffer
 */
public class GuiReader implements AbstractReader {

    private final BufferData buffer;

    /**
     * Init
     */
    public GuiReader(){
        buffer = new BufferData();
    }

    /**
     *
     * @return the buffer
     */
    public BufferData getBuffer(){
        return buffer;
    }

    /**
     *
     * @param txt text to add to the buffer
     */
    public synchronized void addTxt(String txt){
        buffer.addData(txt);
    }

}
