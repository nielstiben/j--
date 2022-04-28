import javax.imageio.plugins.tiff.GeoTIFFTagSet;

class ForStatementFail {
    public static void main(String[] args) {
        int [] ints = new int[10];
        
        for ( i = "S"; i < 10; i++) {
            
        }

        for ( i = 0; i && 10; i++) {
            
        }

        for ( i = 0; i < 10; i++) {
            ---------
        }
    }
}