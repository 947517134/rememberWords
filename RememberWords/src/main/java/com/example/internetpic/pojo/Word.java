package com.example.internetpic.pojo;
/*单词实体类
* */
public class Word {
    private int id;
    private String word;
    private String explain;
    private String sound;
    private String image;

    public Word(int id, String word, String explain, String sound, String image) {
        this.id = id;
        this.word = word;
        this.explain = explain;
        this.sound = sound;
        this.image = image;
    }

    public Word() {
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getExplain() {
        return explain;
    }

    public String getSound() {
        return sound;
    }

    public String getImage() {
        return image;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", explain='" + explain + '\'' +
                ", sound='" + sound + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
