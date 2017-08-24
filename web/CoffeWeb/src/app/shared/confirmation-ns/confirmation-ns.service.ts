export interface Confirmation {
    title?: string;
	message?: string; 	
	yesClicked?: Function;
    hiddenClose: boolean;
}