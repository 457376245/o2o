package cn.jh.dto;

import java.io.InputStream;

public class ImageHolder {
    private String imageName;
    private InputStream image;

    @Override
    public String toString() {
        return "ImageHolder{" +
                "imageName='" + imageName + '\'' +
                ", image=" + image +
                '}';
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public ImageHolder() {
    }

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }
}
