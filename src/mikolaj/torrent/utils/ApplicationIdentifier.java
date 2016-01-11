/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.utils;

public class ApplicationIdentifier {
    private static String identifier = null;

    public static String getIdentifier() {
        if (identifier == null) {
            identifier = Long.toHexString(Double.doubleToLongBits(Math.random()));
        }

        return identifier;
    }
}
