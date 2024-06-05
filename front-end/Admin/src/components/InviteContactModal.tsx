import React, {useEffect, useState} from "react";
import {
    Form,
    Button,
    Modal,
    ModalHeader,
    ModalBody,
    ModalFooter,
    Label,
    Input,
} from "reactstrap";

interface DataTypes {
    email: string | null;
    name: string | null;
    message: string | null;
}

interface InviteContactModalProps {
    isOpen: boolean;
    onClose: () => void;
    onInvite: (data: any) => void;
}

const InviteContactModal = ({
                                isOpen,
                                onClose,
                                onInvite,
                            }: InviteContactModalProps) => {
    /*
    data input handeling
    */
    const [data, setData] = useState<DataTypes>({
        email: null,
        name: null,
        message: null,
    });
    useEffect(() => {
        setData({
            email: null,
            name: null,
            message: null,
        });
    }, []);

    const onChangeData = (field: "email" | "name" | "message", value: string) => {
        let modifiedData: DataTypes = {...data};
        if (value === "") {
            modifiedData[field] = null;
        } else {
            modifiedData[field] = value;
        }
        setData(modifiedData);
    };
    const handleInvite = async () => {
            const {email, name, message} = data;

            if (!email || !name || !message) {
                // Handle validation errors (optional)
                console.error("Please fill in all fields");
                return;
            }

            try {
                const contactResponse = await fetch("http://localhost:9090/api/v1/contacts", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        emailContact: email,
                        name: name,
                        invitationMessage: message,
                    }),
                });

                if (!contactResponse.ok) {
                    throw new Error(
                        `Contact creation failed with status ${contactResponse.status}`
                    );
                }

                const contactData = await contactResponse.json();
                const contactId = 13;
                const userId = 1;

                // If contact creation is successful, create the chat
                if (contactData.message === "contact.add.success") {
                    const chatResponse = await fetch("http://localhost:9090/api/v1/chats", {
                        method: "POST",
                        headers: {"Content-Type": "application/json"},
                        body: JSON.stringify({
                            participantIds: [contactId, userId], // Assuming '13' is the current user's ID
                            chatType: "ONE_ON_ONE",
                        }),
                    });

                    if (!chatResponse.ok) {
                        throw new Error(
                            `Chat creation failed with status ${chatResponse.status}`
                        );
                    }
                    const chatData = await chatResponse.json();
                    const chatID = chatData.data.id;
                    const currentTime = new Date();
                    const formattedTime = currentTime.toISOString();
                    if (chatData.message === "Chat created successfully") {
                        const messResponse = await fetch(
                            "http://localhost:9090/api/v1/chats/"+chatID+"/messages", {
                                method: "POST",
                                headers: {"Content-Type": "application/json"},
                                body: JSON.stringify({
                                    senderId: userId,
                                    content: message,
                                    timestamp: formattedTime,
                                }),
                            });

                        if (!messResponse.ok) {
                            throw new Error(
                                `Chat creation failed with status ${chatResponse.status}`
                            );
                        }
                    }
                    // Handle successful chat creation (optional)
                    console.log("Chat created successfully!");

                    // Reset data, close modal, and call onInvite if provided
                    setData({email: null, name: null, message: null});
                    onClose();
                    if (onInvite) {
                        onInvite(data);
                    }
                }
            } catch
                (error) {
                console.error("Error creating contact or chat:", error);
                // Handle errors (optional)
            }
        }
    ;
    /*
    validation
    */
    // const [valid, setValid] = useState<boolean>(false);
    // useEffect(() => {
    //   if (data.email !== null && data.message !== null && data.name !== null) {
    //     setValid(true);
    //   } else {
    //     setValid(false);
    //   }
    // }, [data]);
    return (
        <Modal isOpen={isOpen} toggle={onClose} tabIndex={-1} centered scrollable>
            <ModalHeader toggle={onClose} className="bg-primary">
                <div className="modal-title-custom text-white font-size-16 ">
                    Create Contact
                </div>
            </ModalHeader>
            <ModalBody className="p-4">
                <Form>
                    <div className="mb-3">
                        <Label htmlFor="AddContactModalemail-input" className="form-label">
                            Email
                        </Label>
                        <Input
                            type="email"
                            className="form-control"
                            id="AddContactModalemail-input"
                            placeholder="Enter Email"
                            value={data["email"] || ""}
                            onChange={(e: any) => {
                                onChangeData("email", e.target.value);
                            }}
                        />
                    </div>
                    <div className="mb-3">
                        <Label htmlFor="AddContactModalname-input" className="form-label">
                            Name
                        </Label>
                        <Input
                            type="text"
                            className="form-control"
                            id="AddContactModalname-input"
                            placeholder="Enter Name"
                            value={data["name"] || ""}
                            onChange={(e: any) => {
                                onChangeData("name", e.target.value);
                            }}
                        />
                    </div>
                    <div className="">
                        <Label
                            htmlFor="AddContactModal-invitemessage-input"
                            className="form-label"
                        >
                            Invatation Message
                        </Label>
                        <textarea
                            value={data["message"] || ""}
                            onChange={(e: any) => {
                                onChangeData("message", e.target.value);
                            }}
                            className="form-control"
                            id="AddContactModal-invitemessage-input"
                            rows={3}
                            placeholder="Enter Message"
                        ></textarea>
                    </div>
                </Form>
            </ModalBody>
            <ModalFooter>
                <Button type="button" color="link" className="btn" onClick={onClose}>
                    Close
                </Button>
                <Button
                    type="button"
                    color="primary"
                    // disabled={!valid}
                    onClick={() => handleInvite()}
                >
                    Invite
                </Button>
            </ModalFooter>
        </Modal>
    );
};

export default InviteContactModal;
