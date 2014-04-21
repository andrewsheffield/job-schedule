/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enumtest;

/**
 *
 * @author sheff
 */
public class EnumTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Port.TCP.getPortNumber());
    }
    
    public enum Port{
        TCP (80),
        UDP (120),
        LDAP (1);

        private int portNumber;
        private Port(int portNumber) {
            this.portNumber = portNumber;
        }
        public int getPortNumber() {
            return portNumber;
        }
    }
    
    
    
}
