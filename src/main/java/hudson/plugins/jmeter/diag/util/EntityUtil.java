package hudson.plugins.jmeter.diag.util;

import hudson.plugins.jmeter.diag.entity.HttpSample;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by blorenz on 19.05.16.
 */
public class EntityUtil {
    /**
     * Builds a hash for the given sample.
     *
     * @param sample httpSample to build hash from.
     *
     * @return SHA1 hash in hex representation
     */
    public static String buildHash(HttpSample sample) {
        return DigestUtils.sha1Hex(sample.toString());
    }
}
