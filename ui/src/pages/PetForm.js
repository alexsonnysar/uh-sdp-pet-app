import React from "react";
import { useForm } from "react-hook-form";

export default function PetForm() {
  const { register, errors, handleSubmit } = useForm();

  return (
    <form onSubmit={handleSubmit(OnSubmit)}>
      <input name="name" ref={register({ required: true })} />
      {errors.name && "Name is required"}
      <input name="type" ref={register({ required: true })} />
      {errors.type && "Type is required"}
      <input name="sex" ref={register({ required: true })} />
      {errors.sex && "Please select a gender"}
      <input name="age" ref={register({ required: true })} />
      {errors.age && "Could you please estimate the age"}
      <input name="size" ref={register({ required: true })} />
      {errors.size && "Could you please estimate the size"}
      <input name="weight" ref={register({ required: true })} />
      {errors.weight && "weight is required"}
      <input name="dateAdded" ref={register({ required: true })} />
      {errors.dateAdded && "CAN WE MAKE THIS DEFAULT TO NOW"}
      <input name="description" ref={register({ required: true })} />
      {errors.description && "what makes NAME special?"}
      <input name="uploadImages" ref={register({ required: true })} />
      {errors.uploadImages && "Please upload at lease one image"}
      <input name="active" ref={register({ required: true })} />
      {errors.active && "Please indicate if active"}
      <input name="adopted" ref={register({ required: true })} />
      {errors.adopted && "Please indicate if adopted"}

      <input type="submit" />
    </form>
  );
}
