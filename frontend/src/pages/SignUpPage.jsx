import { useState } from 'react';

function SignUpPage() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState('');

  const isValidEmail = (text) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(text);
  };

  const handleSignUp = async () => {
    try {
      const response = await fetch('http://localhost:3001/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name,
          email,
          password,
        })
      });

      if (!response.ok) {
        throw new Error(response.statusText);
      }

      const data = await response.json();
      //todo set token
      console.log("Created user: ", data);

    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-[#121A17] px-4">

      <div className="bg-[#1E2C26] p-8 rounded-3xl shadow-xl w-full max-w-md border border-[#2A3A33]">

        <h1 className="text-4xl font-bold mb-2 text-center text-[#C2A878]">
          Trails N Camping
        </h1>

        <p className="text-center text-[#A7B0AA] mb-8">
          Plan your next outdoor adventure
        </p>

        <div className="flex flex-col gap-4">

          <input
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="
              border border-[#2A3A33]
              bg-[#18231F]
              text-[#E6E6E6]
              rounded-xl
              px-4 py-3
              focus:outline-none
              focus:ring-2 focus:ring-[#2F5D50]
              transition
            "
          />

          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => {
              const value = e.target.value;
              setEmail(value);

              if (value && !isValidEmail(value)) {
                setEmailError("Invalid email address");
              } else {
                setEmailError("");
              }
            }}
            className="
              border border-[#2A3A33]
              bg-[#18231F]
              text-[#E6E6E6]
              rounded-xl
              px-4 py-3
              focus:outline-none
              focus:ring-2 focus:ring-[#2F5D50]
              transition
            "
          />

          {emailError && (
            <p className="text-red-400 text-sm">
              {emailError}
            </p>
          )}

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="
              border border-[#2A3A33]
              bg-[#18231F]
              text-[#E6E6E6]
              rounded-xl
              px-4 py-3
              focus:outline-none
              focus:ring-2 focus:ring-[#2F5D50]
              transition
            "
          />

          <button
            disabled={!name || !email || !password || emailError}
            onClick={handleSignUp}
            className="
              bg-[#2F5D50]
              hover:bg-[#1E3D35]
              text-white
              py-3
              rounded-xl
              font-semibold
              transition
              mt-2
              shadow-md
              disabled:bg-[#2A3A33]
              disabled:text-[#6B7A73]
              disabled:cursor-not-allowed
            "
          >
            Create Account
          </button>

        </div>
      </div>
    </div>
  )
}

export default SignUpPage;