import { useState } from "react";
import styles from "./AuthFlipCard.module.css";
import LoginForm from "./LoginForm";
import RegisterForm from "./RegisterForm";

export default function AuthFlipCard() {
  const [isFlipped, setIsFlipped] = useState(false);
  const [emailToPrefill, setEmailToPrefill] = useState("");

  const handleSuccessfulRegistration = (email: string) => {
    setEmailToPrefill(email);
    setIsFlipped(false); // flip to login
  };

  return (

    
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className={styles.flipContainer}>
        <div className={`${styles.flipper} ${isFlipped ? styles.flipped : ""}`}>
          
          {/* Front Side - Login */}
          <div className={styles.front}>
            <LoginForm prefillEmail={emailToPrefill} />
            <div className="text-center mt-4">
              <p
                onClick={() => setIsFlipped(true)}
                className="text-blue-500 hover:underline cursor-pointer"
              >
                Don't have an account? Register
              </p>
            </div>
          </div>

          {/* Back Side - Register */}
          <div className={styles.back}>
            <RegisterForm onSuccess={handleSuccessfulRegistration} />
            <div className="text-center mt-4">
              <p
                onClick={() => setIsFlipped(false)}
                className="text-blue-500 hover:underline cursor-pointer"
              >
                Already have an account? Login
              </p>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}
