import { useState } from 'react';

function SignUpPage() {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const handleSignUp = () =>{

  };

  return (
    <>
      <input
        type="text"
        value={name}
        onChange={(e) =>{
          setName(e.target.value)
        }}
      >
      </input>

      <input
        type="text"
        value={email}
        onChange={(e) =>{
          setEmail(e.target.value)
        }}
      >
      </input>

      <input
        type="text"
        value={password}
        onChange={(e) =>{
          setPassword(e.target.value)
        }}
      >
      </input>

      <button
        disabled={!name || !email || !password}
        onClick={handleSignUp}
      >
        Sign Up!
      </button>
    </>
  )
}

export default SignUpPage
