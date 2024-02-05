package ma.youcode.myrhbackendapi.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.logging.Logger;

/**
 * {@link Env} - class is responsible for loading environment variables from a .env file
 */
public class Env {
    volatile private static Dotenv env = null;
    static {
        Logger.getLogger(Env.class.getName()).info("Loading Environment variables...");
        synchronized (Env.class) {
            if (env == null) {
                env = Dotenv.configure()
                        .ignoreIfMalformed()
                        .ignoreIfMissing()
                        .load();
            }
        };
    }

    /**
     * Get the value of an environment variable by its key
     * @param key - the key of the environment variable
     * @return the value of the environment variable or null if not found
     */
    public static String get(String key) {
        return env.get(key);
    }
}
