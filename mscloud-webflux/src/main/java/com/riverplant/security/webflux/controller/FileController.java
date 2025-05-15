package com.riverplant.security.webflux.controller;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileController {

    /*
    要傳送這樣的請求，你可以使用 Postman 或前端表單傳送如下:
     * POST /upload
     * Content-Type: multipart/form-data
     *
     * --boundary
     * Content-Disposition: form-data; name="meta-data"
     * Content-Type: application/json
     *
     * {"name":"myfile", "type":"pdf"}
     *
     * --boundary
     * Content-Disposition: form-data; name="file-data"; filename="test.pdf"
     * Content-Type: application/pdf
     *
     * <file contents>
     * --boundary--
     */

    /**
     *
     * 使用 @RequestPart 獲得metadata 和 file
     * @param metadata
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Mono<String> fileUpload(@RequestPart("meta-data") Part metadata,
                             @RequestPart("file-data")FilePart file) {

      //  file.transferTo()// 使用零拷貝技術
        Path uploadDir = Paths.get("/temp/uploads/");
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            return Mono.error(new RuntimeException("無法建立上傳目錄", e));
        }
       Path path = Paths.get(uploadDir + file.filename());
        return file.transferTo(path)
                .then(Mono.just("文件已經存儲至: " + path.toString()));

    }

    @PostMapping(value = "/uploads", consumes = "multipart/form-data")
    public Mono<String> uploadFiles(@RequestPart("meta-data") MetadataDto metadata,
                                    @RequestPart("file-data") Flux<FilePart> files) {

        Path uploadDir = Paths.get("/temp/uploads/");
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            return Mono.error(new RuntimeException("無法建立上傳目錄", e));
        }

        return files
                .flatMap(file->{
                  String filename = metadata.name() + "-" + file.filename();
                  Path path = Paths.get( uploadDir+ filename);
                  return file.transferTo(path);
                })
                .then(Mono.just("已上傳所有檔案，根據metadata: " + metadata.name()));

    }

}

class MyForm {
    private String name;

    private MultipartFile file;


}

