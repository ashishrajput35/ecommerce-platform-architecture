import React from "react";
import './Input.module.css';


interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className = "", ...props }, ref) => {
    return (
      <div className={`input-data-wrapper ${className}`}>
        <input ref={ref} className="input-data-input" {...props} required />
        <span className="border-animated"></span>
        {props.id && (
          <label className="input-data-label" htmlFor={props.id}>
            {props.placeholder}
          </label>
        )}
      </div>
    );
  }
);

Input.displayName = "Input";

export { Input };
