import { FormControl, InputLabel, MenuItem } from "@mui/material";
import Select, { SelectChangeEvent } from '@mui/material/Select';


const SelectComp = (props: any) => 
(<FormControl fullWidth>
    <InputLabel id="select-label">{props.label}</InputLabel>
    <Select
        labelId="select-label"
        key="select"
        id="select"
        value={props.val}
        label={props.label}
        onChange={(e: SelectChangeEvent) => props.setValue(e.target.value as string)}
    >
        {props.options.map((opt: string) => <MenuItem key={opt} value={opt}>{opt}</MenuItem>)}
    </Select>
</FormControl>)

export default SelectComp