package Controller;

import Entities.FileEntity;
import Model.FileCrud;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class FileController implements Serializable {

    @Inject
    private FileCrud fileCrud;

    byte[] file;
    private FileEntity fileEntity;
    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    @PostConstruct
    public void init() {
        fileEntity = new FileEntity();
    }

    public void saveFile() {
        System.out.println("uploading fileeee" + uploadedFile.getFileName());
        saveData();
        if (uploadedFile != null) {
            fileEntity.setFilename(uploadedFile.getFileName());
        }
        if (fileEntity.getId() == null) {
            fileCrud.save(fileEntity);
        }
    }

    public void saveUploadedFile(FileUploadEvent event) throws IOException {
        this.uploadedFile = event.getFile();
        InputStream inputs = event.getFile().getInputstream();
        this.file = IOUtils.toByteArray(inputs);
    }

    public String saveData() {
        if (this.file!= null) {

            try {
                String uploadFolderPath = "/home/saugat/Desktop/FileFolder";
                Path folderPath = Paths.get(uploadFolderPath);
                Path destinationPath = folderPath.resolve(uploadedFile.getFileName());
                Files.write(destinationPath, file);
                return "/FileFolder/" + uploadedFile.getFileName();
            } catch (IOException e) {
                e.printStackTrace();
                FacesMessage message = new FacesMessage("Error",
                        "Error while uploading the file.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } else {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Invalid file type.",
                    "Please upload .pdf or .jpg files only.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }

    public boolean isFileTypeAllowed(UploadedFile uploadedFile) {
        String fileName = uploadedFile.getFileName();
        if (fileName != null) {
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            return "jpg".equalsIgnoreCase(extension) || "pdf".equalsIgnoreCase(extension);
        }
        return false;
    }
}
