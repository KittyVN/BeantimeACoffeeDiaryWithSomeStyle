export interface SubredditsResponse {
  data: {
    children: Array<{
      data: {
        display_name_prefixed: string;
      };
    }>;
  };
}
