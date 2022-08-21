package ru.chervoniy;

import java.security.KeyStore;
import java.util.function.Supplier;

public class EsiaSignerBuilder {

    private static final String DEFAULT_SIGNATURE_PROVIDER = "BC";
    private static final String DEFAULT_SIGNATURE_ALGORITHM = "GOST3411-2012-256WITHECGOST3410-2012-256";

    private Supplier<String> signingAlgorithmSupplier = () -> DEFAULT_SIGNATURE_ALGORITHM;
    private Supplier<String> signatureProviderSupplier = () -> DEFAULT_SIGNATURE_PROVIDER;
    private Supplier<KeyStore> keyStoreSupplier;
    private Supplier<String> signingCertificateAliasSupplier;
    private Supplier<String> privateKeyPasswordSupplier;
    private Supplier<Boolean> detachedFlagSupplier = () -> true;

    static EsiaSignerBuilder builder() {
        return new EsiaSignerBuilder();
    }

    /**
     * @param signingAlgorithmSupplier signature algorithm, default = {@value DEFAULT_SIGNATURE_ALGORITHM}
     * @return this
     */
    public EsiaSignerBuilder signingAlgorithmSupplier(Supplier<String> signingAlgorithmSupplier) {
        this.signingAlgorithmSupplier = signingAlgorithmSupplier;
        return this;
    }

    /**
     * @param signatureProviderSupplier signature algorithm, default = {@value DEFAULT_SIGNATURE_PROVIDER}
     * @return this
     */
    public EsiaSignerBuilder signatureProviderSupplier(Supplier<String> signatureProviderSupplier) {
        this.signatureProviderSupplier = signatureProviderSupplier;
        return this;
    }

    /**
     * Supplier example
     * () -> {
     *  try (InputStream stream = new FileInputStream("path/to/keystore.p12")) {
     *      KeyStore store = KeyStore.getInstance("PKCS12");
     *      store.load(stream, "p@ssword".toCharArray());
     *      return store;
     *  } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException e) {
     *      throw new RuntimeException(e);
     *  }
     * };
     *
     * @param keyStoreSupplier key store supplier, example:
     * @return this
     */
    public EsiaSignerBuilder keyStoreSupplier(Supplier<KeyStore> keyStoreSupplier) {
        this.keyStoreSupplier = keyStoreSupplier;
        return this;
    }

    public EsiaSignerBuilder signingCertificateAliasSupplier(Supplier<String> signingCertificateAliasSupplier) {
        this.signingCertificateAliasSupplier = signingCertificateAliasSupplier;
        return this;
    }

    public EsiaSignerBuilder privateKeyPasswordSupplier(Supplier<String> privateKeyPasswordSupplier) {
        this.privateKeyPasswordSupplier = privateKeyPasswordSupplier;
        return this;
    }

    /**
     * @param detachedFlagSupplier signature detached flag, default = true
     * @return this
     */
    public EsiaSignerBuilder detachedFlagSupplier(Supplier<Boolean> detachedFlagSupplier) {
        this.detachedFlagSupplier = detachedFlagSupplier;
        return this;
    }

    public EsiaSigner build() {
        return new EsiaSigner(this.signingAlgorithmSupplier, this.signatureProviderSupplier, this.keyStoreSupplier,
                this.signingCertificateAliasSupplier, this.privateKeyPasswordSupplier, this.detachedFlagSupplier);
    }
}
