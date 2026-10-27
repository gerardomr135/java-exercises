package com.fireal99.Interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface Input {
    byte[] read(InputStream data)  throws IOException;
}
