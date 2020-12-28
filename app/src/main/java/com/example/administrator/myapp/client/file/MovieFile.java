package com.example.administrator.myapp.client.file;

import java.io.File;

public class MovieFile extends BaseFile {
    MovieFile(File file) {
        super(file);
    }

    MovieFile(String fileName, String fileExtensionName, int fileSize, int filePackCount) {
        super(fileName, fileExtensionName, fileSize, filePackCount);
    }
}
