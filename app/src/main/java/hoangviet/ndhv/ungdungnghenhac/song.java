package hoangviet.ndhv.ungdungnghenhac;

public class song {
    private String tittle ;
    private int file;

    public song(String tittle, int file) {
        this.tittle = tittle;
        this.file = file;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
