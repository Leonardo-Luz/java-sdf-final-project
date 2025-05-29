package ifrs.edu.br.cli;

/**
 * Version
 */
public class Version {
    public static void command() {
        Package pkg = Version.class.getPackage();
        String version = (pkg != null) ? pkg.getImplementationVersion() : null;

        if (version != null) {
            System.out.println("Version " + version);
        } else {
            System.out.println("Review CLI version information not available.");
        }
    }
}
