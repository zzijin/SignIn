package com.example.administrator.myapp.client.file;

import java.io.File;

public class MusicFile extends BaseFile {
    MusicFile(File file) {
        super(file);
    }

    MusicFile(String fileName, String fileExtensionName, int fileSize, int filePackCount) {
        super(fileName, fileExtensionName, fileSize, filePackCount);
    }
}
