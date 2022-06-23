package vamigo.vamigoPJ.Service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import vamigo.vamigoPJ.entity.Image;
import vamigo.vamigoPJ.repository.ImageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private String path = "./images";
    File folder = new File(path);
    File[] fileList = folder.listFiles();
    String[] filenames = folder.list();
    private final ImageRepository imageRepository;


    public void ImageToDB() {
        int i = 0;
        for (File file : fileList) {

            String encodedfile = null;

            String[] name = filenames[i].split("\\.");
            Long id = Long.parseLong(name[0]);

            if (file.isFile() && file.canRead()) {

                try {
                    FileInputStream fileInputStreamReader = new FileInputStream(file);
                    byte[] bytes = new byte[(int)file.length()];
                    fileInputStreamReader.read(bytes);
                    encodedfile = new String(Base64.encodeBase64(bytes),"UTF-8");
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageRepository.save(Image.builder().id(id).image(encodedfile).build());
            }
            i++;
        }
    }
}
