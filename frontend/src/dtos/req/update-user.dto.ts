export interface UpdateUserDto {
  id: number;
  email: string;
  password: string;
  newPassword?: string;
}
