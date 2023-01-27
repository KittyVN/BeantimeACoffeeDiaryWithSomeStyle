export interface UpdateUserDto {
  id: number;
  email: string;
  username: string;
  password: string;
  newPassword?: string;
}
