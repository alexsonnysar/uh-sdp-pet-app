import React from "react";
import { useForm } from "react-hook-form";

export default function PetForm() {
  const { register, errors, handleSubmit } = useForm();
  const onSubmit = (data, e) => {
    console.log("Submit event", e);
    alert(JSON.stringify(data));
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <label>Name</label>
      <input
        type="text"
        placeholder="Boomer"
        name="Name"
        ref={register({ required: true, maxLength: 80 })}
      />
      {errors.name && "Name is required"}
      <label>Type</label>
      <select name="Type" ref={register({ required: true })}>
        {errors.type && "Type is required"}
        <option value="Dog">Dog</option>
        <option value="Cat">Cat</option>
        <option value="Bird">Bird</option>
        <option value="Fish">Fish</option>
        <option value="Lizard">Lizard</option>
        <option value="Furry">Furry</option>
        <option value="Farm Animal">Farm Animal</option>
        <option value="Bat">Bat</option>
      </select>
      <label> Male</label>
      <input
        name="Sex"
        type="radio"
        value="Male"
        ref={register({ required: true })}
      />
      <label> Female</label>
      <input
        name="Sex"
        type="radio"
        value="Female"
        ref={register({ required: true })}
      />
      {errors.sex && "Please select a gender"}
      <label> Age</label>
      <select name="Age" ref={register({ required: true })}>
        {errors.age && "Could you please estimate the age"}
        <option value="Baby">Baby</option>
        <option value="Young">Young</option>
        <option value="Adult">Adult</option>
        <option value="Jurassic">Jurassic</option>
      </select>
      <label> Size</label>
      <select name="Size" ref={register({ required: true })}>
        {errors.size && "Could you please estimate the size"}
        <option value="Small">Small</option>
        <option value="Medium">Medium</option>
        <option value="Large">Large</option>
      </select>
      <label> Weight </label>
      <input
        type="number"
        placeholder="Weight"
        name="Weight"
        ref={register({ required: true })}
      />
      {errors.weight && "weight is required"}
      <label> Arrival Date </label>
      <input
        type="date"
        name="Arrival Date"
        placeholder={Date()}
        ref={register({ required: true })}
      />
      {errors.dateAdded && "CAN WE MAKE THIS DEFAULT TO NOW"}
      <label> Description </label>
      <textarea name="Description" ref={register({ required: true })} />
      {errors.description && "what makes NAME special?"}
      <label> Images </label>
      <input
        type="url"
        placeholder="Upload Image"
        name="Upload Image"
        ref={register({ required: true })}
      />
      {errors.uploadImages && "Please upload at lease one image"}
      <label> Active </label>
      <label>true</label>
      <input name="Active" type="radio" value="true" ref={register} />
      {errors.active && "Please indicate if active"}
      <label>false</label>
      <input name="Active" type="radio" value="false" ref={register} />
      {errors.adopted && "Please indicate if adopted"}
      <label> Adopted </label>
      <label>true</label>
      <input
        name="adopted"
        type="radio"
        value="true"
        ref={register({ required: true })}
      />
      <label>false</label>
      <input
        name="adopted"
        type="radio"
        value="false"
        ref={register({ required: true })}
      />
      <input type="submit" />
    </form>
  );
}
